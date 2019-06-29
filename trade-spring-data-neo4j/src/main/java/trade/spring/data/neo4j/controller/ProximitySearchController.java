package trade.spring.data.neo4j.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import trade.spring.data.neo4j.apiModel.GeneralResponse;
import trade.spring.data.neo4j.domain.node.Company;
import trade.spring.data.neo4j.repositories.CompanyRepository;
import trade.spring.data.neo4j.services.ProximitySearchService;

import java.util.List;

/**
 * Created by Wayne.A.Z on 2019-05-19.
 */
@RestController
@RequestMapping("/proximity")
public class ProximitySearchController {

    @Autowired
    private ProximitySearchService proximitySearchService;

    /**
     * Prepare data from graph for training and testing.
     *
     * Procedure:
     * 1.Sampling by random walk.
     * 2.Generate entity features by information from graph.
     * 3.Generate sub-paths from samplings from step 1.
     *
     * Finally, these results are written to a file.
     *
     * Attention:
     * 	If you want to generate data for symmetric training data, then you should remove "GenerateEntitiesFeaturesByGraph.main(null);" and only use "GenerateEntitiesFeatureByTypes.main(null);".
     *  While if you want to generate data for asymmetric training data, then you should remove "GenerateEntitiesFeatureByTypes.main(null);" and only use "GenerateEntitiesFeaturesByGraph.main(null);".
     */
    @GetMapping("/randomWalkSampling")
    @ResponseBody
    @ApiOperation("获得节点特征向量和随机游走获得子路径")
    public GeneralResponse findAllCompanies() {

        long starttime=System.currentTimeMillis();

        System.out.println("Start random walk sampling......");
        proximitySearchService.randomWalkSampling(50,5);//todo:参数提取出来手动输入

        System.out.println("Start generating entities' features......");
        proximitySearchService.generateEntitiesFeatureByTypes();

        System.out.println("Start generating sub-paths from samplings......");
        proximitySearchService.generateSubPathsFromSamplings();

        long endtime=System.currentTimeMillis();
        System.out.println("Cost time == "+(endtime-starttime)+" ms");

        GeneralResponse<List<Company>> resp = new GeneralResponse<>();
        return resp;
    }

}
