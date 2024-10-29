package com.avocado.ecomus.service.imp;

import com.avocado.ecomus.entity.PaymentInformationEntity;
import com.avocado.ecomus.exception.UserNotFoundException;
import com.avocado.ecomus.payload.req.AddPaymentInformationRequest;
import com.avocado.ecomus.repository.PaymentInformationRepository;
import com.avocado.ecomus.repository.UserRepository;
import com.avocado.ecomus.security.EncryptionUtil;
import com.avocado.ecomus.service.PaymentInformationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class PaymentInformationServiceImp implements PaymentInformationService {

    private final PaymentInformationRepository paymentInformationRepository;
    private final UserRepository userRepository;

    @Value("${encryption.secret}")
    private String key;

    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public PaymentInformationServiceImp(
            PaymentInformationRepository paymentInformationRepository
            , UserRepository userRepository) {
        this.paymentInformationRepository = paymentInformationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void addPaymentInformation(AddPaymentInformationRequest request, int userId) throws Exception {
        PaymentInformationEntity paymentInformationEntity = new PaymentInformationEntity();
        paymentInformationEntity.setHolderName(request.holderName());
        paymentInformationEntity.setBank(request.bank());
        paymentInformationEntity.setCcv(EncryptionUtil.encrypt(request.ccv(), getSecretKey()));
        paymentInformationEntity.setCardNumber(EncryptionUtil.encrypt(request.cardNumber(), getSecretKey()));
        paymentInformationEntity.setExpired(request.expired());
        paymentInformationEntity.setUser(
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"))
        );

        paymentInformationRepository.save(paymentInformationEntity);
    }
}
