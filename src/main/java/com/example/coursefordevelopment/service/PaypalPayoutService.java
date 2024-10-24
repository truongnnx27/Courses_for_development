package com.example.coursefordevelopment.service;

import com.paypal.api.payments.Payout;
import com.paypal.api.payments.PayoutBatch;
import com.paypal.api.payments.PayoutItem;
import com.paypal.api.payments.Currency;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaypalPayoutService {

    @Autowired
    private APIContext apiContext;

    // Tạo một payout đơn lẻ (rút tiền)
    public PayoutBatch createPayout(String receiverUserId, Double amount, String note) throws PayPalRESTException {
        // Tạo đối tượng PayoutItem
        PayoutItem payoutItem = new PayoutItem();
        payoutItem.setRecipientType("EMAIL"); // Sử dụng EMAIL cho recipient type
        payoutItem.setReceiver(receiverUserId); // PayPal ID hoặc email của người nhận
        payoutItem.setAmount(new Currency().setCurrency("USD").setValue(String.format("%.2f", amount)));
        payoutItem.setSenderItemId(UUID.randomUUID().toString()); // Mã ID duy nhất cho mỗi giao dịch
        payoutItem.setNote(note); // Ghi chú cho giao dịch (tuỳ chọn)

        // Tạo danh sách payout items
        List<PayoutItem> items = new ArrayList<>();
        items.add(payoutItem);

        // Tạo đối tượng Payout
        Payout payout = new Payout();
        payout.setSenderBatchHeader(new com.paypal.api.payments.PayoutSenderBatchHeader()
                .setSenderBatchId(UUID.randomUUID().toString()) // Mã batch ID duy nhất
                .setEmailSubject("You have a payout!")); // Tiêu đề email thông báo cho người nhận

        payout.setItems(items);
        Map<String, String> params = new HashMap<>();
        params.put("sync_mode", "false");  // Chỉ định chế độ bất đồng bộ

        // Gửi yêu cầu payout tới PayPal
        return payout.create(apiContext, params);

    }

}
