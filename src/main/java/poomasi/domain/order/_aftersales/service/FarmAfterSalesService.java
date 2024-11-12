package poomasi.domain.order._aftersales.service;


import org.springframework.stereotype.Service;
import poomasi.domain.order._aftersales.dto.cancel.request.FarmCancelRequest;

@Service
public class FarmAfterSalesService {

    public String farmCancel(FarmCancelRequest farmCancelRequest){
        return "success!";
    }

}
