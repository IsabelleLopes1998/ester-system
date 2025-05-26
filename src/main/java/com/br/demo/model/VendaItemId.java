package com.br.demo.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VendaItemId implements Serializable {
	private UUID produtoId;
	private UUID vendaId;
}