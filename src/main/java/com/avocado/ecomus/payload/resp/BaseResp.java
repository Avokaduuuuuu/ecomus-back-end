package com.avocado.ecomus.payload.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResp {
    private int code = 200;
    private String msg = "OK";
    private Object data;
}
