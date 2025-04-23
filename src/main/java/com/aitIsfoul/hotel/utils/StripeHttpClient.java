package com.aitIsfoul.hotel.utils;

import com.aitIsfoul.hotel.config.StripeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Component
public class StripeHttpClient {


    public Session createSession(SessionCreateParams params) throws Exception {
        return Session.create(params.toMap());
    }
}
