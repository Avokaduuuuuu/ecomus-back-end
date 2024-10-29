package com.avocado.ecomus.service;

import com.avocado.ecomus.payload.req.AddPaymentInformationRequest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PaymentInformationService {
    void addPaymentInformation(AddPaymentInformationRequest request, int userId) throws Exception;
}
