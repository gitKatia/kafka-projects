package com.kat.kafkaorderproducer.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PremiumPurchaseRequest {
	private String item;
	private String purchaseNumber;
	private String username;
}
