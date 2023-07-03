package com.training.program.finalproject2.service.interfaces;

import com.training.program.finalproject2.request.CheckOutRequest;
import com.training.program.finalproject2.response.CompleteCheckoutResponse;

public interface LogicService {

    void startCheckout(CheckOutRequest checkOutRequest);

    CompleteCheckoutResponse endCheckout(int id);
}
