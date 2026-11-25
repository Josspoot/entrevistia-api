package mx.edu.tecdesoftware.entrevistia.domain;

import java.util.List;

/**
 * Respuesta del asistente: texto en lenguaje natural y los IDs de las
 * vacantes que menciona, para que la app las muestre como tarjetas.
 */
public record AssistantReply(String reply, List<Integer> vacancyIds) {
}
