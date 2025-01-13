//package org.paolo.drumkit_.dto.response;
//
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.Data;
//
//@Data
//public class IndirizzoResponseDTO {
//    @NotBlank(message = "devi inserire un nome del destinatario valido")
//    private String nome;
//
//    @NotBlank(message = "devi inserire almeno un indirizzo")
//    private String indirizzoR1;
//    private String indirizzoR2;
//
//    @NotBlank(message = "inserire un cap valido")
//
//    @Size(min = 5 ,max = 5 ,  message ="il cap ha 5 numeri non di piu non di meno")
//    private String cap;
//    @NotBlank(message = "inserire un comunqe valido")
//    private String comune;
//    @NotBlank(message = "inserire uno stato valido")
//    private String stato;
//    @NotBlank(message = "inserire un numero di telefono valido")
//
//    @Size(min = 10,  message ="i numeri di telefono hanno almeno 10 cifre")
//    @Pattern(regexp = "^[1-9]\\d{2}\\s\\d{3}\\s\\d{4}",
//            message="brutto coglione quello non è un numero di telefono")
//    private String numeroTel;
//
//}
