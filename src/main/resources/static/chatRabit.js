
sendMessage().then(() => console.log('Invio messaggio avviato'));

async function receiveMessages() {
    const exchange = 'ExchangeDurable'; // Nome dell'exchange
    const queue = 'listaDurable';       // Nome della coda
    const routingKey = 'topicDiEsempio';       // Chiave di routing

    try {
        // Connettiti a RabbitMQ
        const connection = await amqp.connect('amqp://guest:guest@localhost:5672');
        const channel = await connection.createChannel();

        // Assicura che l'exchange esista ed è durabile
        await channel.assertExchange(exchange, 'direct', { durable: true });

        // Assicura che la coda esista ed è durabile
        await channel.assertQueue(queue, { durable: true });

        // Collega la coda all'exchange con la routing key
        await channel.bindQueue(queue, exchange, routingKey);

        console.log(`[*] Waiting for messages in queue: ${queue}`);

        // Consuma i messaggi dalla coda
        channel.consume(queue, (msg) => {
            if (msg !== null) {
                console.log(`[x] Received: ${msg.content.toString()}`);
                channel.ack(msg); // Conferma che il messaggio è stato elaborato
            }
        });
    } catch (error) {
        console.error('Errore:', error);
    }
}

receiveMessages().then(() => console.log('Ricezione messaggi avviata'));