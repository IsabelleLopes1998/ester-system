package com.br.demo.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class AcertoItemId implements Serializable {
	private UUID idProduto;
	private UUID idAcerto;
}