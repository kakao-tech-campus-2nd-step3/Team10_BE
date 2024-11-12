package poomasi.domain.order._payment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentWebHookRequest(@JsonProperty("imp_uid") String impUid,
                                    @JsonProperty("merchant_uid") String merchantUid,
                                    String status) {
}
