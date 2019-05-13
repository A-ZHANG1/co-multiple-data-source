package trade.spring.data.neo4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trade.spring.data.neo4j.apiModel.GeneralResponse;
import trade.spring.data.neo4j.services.SupplyChainService;

/**
 * Created by huangtao on 2019-05-05.
 */

@RestController
@RequestMapping("/supplychain")
public class SupplyChainController {

    @Autowired
    SupplyChainService supplyChainService;

//    @GetMapping("/generateSupplyChainNodes")
//    public GeneralResponse generateSupplyChainNodes(){
//        GeneralResponse resp = new GeneralResponse<>();
//        resp.setObj(supplyChainService.calSupplyChains());
//        return resp;
//    }
//
//    @GetMapping("/deleteAllSupplyChainNodes")
//    public GeneralResponse deleteAllSupplyChainNodes() {
//        GeneralResponse resp = new GeneralResponse<>();
//        if (!supplyChainService.deleteAllSupplyChainNodes()) {
//            resp.setStatus(2);
//        }
//        return resp;
//    }

}
