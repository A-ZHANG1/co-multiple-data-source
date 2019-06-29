package trade.spring.data.neo4j.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import trade.spring.data.neo4j.apiModel.GeneralResponse;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.services.BMLPACommunityFinderService;
import trade.spring.data.neo4j.services.CommunityDetectionService;
import trade.spring.data.neo4j.supplychain.bmlpa.utils.NodeWeightedGraph;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/communityDetection")
@Api(value = "调用社群发现方法" , produces = "application/json")
public class CommunityDetectionController {

  @Autowired
  CommunityDetectionService communityDetectionService;

  @Autowired
  BMLPACommunityFinderService bmlpaCommunityFinderService;

  @GetMapping("/executeBMLPA")
  @ResponseBody
  @ApiOperation(value = "执行bmlpa算法")
  public GeneralResponse<List<Company>> executeBMLPA(){
    return communityDetectionService.executeBMLPA();
  }

  @GetMapping("/roughCoreByCapital")
  @ResponseBody
  @ApiOperation(value = "高注册资本优先初始化核函数")
  public GeneralResponse<Map<Company, List<Double>>> roughCoreByCapital(){
    return bmlpaCommunityFinderService.roughCoreByCapital();
  }

  @GetMapping("/propagation")
  @ResponseBody
  @ApiOperation(value = "标签传播过程")
  public GeneralResponse<Map<Company, Map<Double, Double>>> propagation(@RequestParam double threshold){
    return bmlpaCommunityFinderService.propagation(threshold);
  }

  @GetMapping("/initNodeWeightedGraph")
  @ResponseBody
  @ApiOperation(value = "检查初始化结果")
  public GeneralResponse<NodeWeightedGraph> initNodeWeightedGraph(){
    GeneralResponse<NodeWeightedGraph>rs = new GeneralResponse<>();
            rs.setObj(bmlpaCommunityFinderService.initNodeWeightedGraph());
    return rs;
  }
}
