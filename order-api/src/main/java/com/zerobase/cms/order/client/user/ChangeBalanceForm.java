package com.zerobase.cms.order.client.user;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeBalanceForm {
    private String from;
    private String message;
    private Integer money;
}
