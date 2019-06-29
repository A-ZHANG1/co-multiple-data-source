package trade.spring.data.neo4j.services;

import org.springframework.stereotype.Service;
import trade.spring.data.neo4j.apiModel.graph.Node;

import java.util.*;

/**
 * Created by Wayne.A.Z on 2019-05-19.
 * 随机采样获得节点嵌入。由于原始数据不具有标签规律性所以训练结果没有保证，暂时终止
 */
@Service
public class ProximitySearchService {

    /**
     * Generate samplings by random walk.
     *
     * @param k : k 条路径
     * @param l : 每条路径长
     */
    String MAIN_DIR = "/Users/winnieaz/Documents/repos/co-multiple-data-source/trade-spring-data-neo4j/toy_data";

    public void randomWalkSampling(int k, int l) {
//        List<Company> path = null;
//        FileWriter writer = null;
//        StringBuilder sb = new StringBuilder();
//        try {
//            writer = new FileWriter(MAIN_DIR + "randomWalkSamplingPaths");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        IdGraph idGraph = new IdGraph();
//
//        BasicOperation basicOperation = new BasicOperation();
//        idGraph.setGraph(basicOperation.getGraphCompanyDim());
//
//        for(Integer node:idGraph.getNodeList()){
//            for(int i=0;i<k;i++){
//                path=randomWalkPath(node,l,data);
//                if(path.size()<shortest_path_length){
//                    continue;
//                }
//                sb.delete( 0, sb.length() );
//                for(int j=0;j<path.size();j++){
//                    sb.append(path.get(j).getId()+" ");
//                }
//                sb.append("\r\n");
//                try {
//                    writer.write(sb.toString());
//                    writer.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
        return;
    }
    /**
     * Generate a path by random walk.
     * @param start
     * @param l
     * @param data
     * @return
     */
    private List<Node> randomWalkPath(Node start, int l, Map<Integer,Node> data){
        List<Node> path=new ArrayList<Node>(l+1);
//        path.add(start);
//        Node now=start;
//        Set<Integer> types_set=new HashSet<Integer>();
//        List<Integer> types=new ArrayList<Integer>();
//        Map<Integer,List<Integer>> neighbours=new HashMap<Integer, List<Integer>>();
//        int type= -1;
//        List<Integer> list=null;
//        for(int i=0;i<l;i++){
//            if(now.out_nodes.size()==0){
//                break;
//            }
//            types_set.clear();
//            types.clear();
//            neighbours.clear();
//            for(Node n:now.out_nodes){
//                types_set.add(n.getTypeId());
//                if(neighbours.containsKey(n.getTypeId())){
//                    neighbours.get(n.getTypeId()).add(n.getId());
//                }
//                else{
//                    List<Integer> ids=new ArrayList<Integer>();
//                    ids.add(n.getId());
//                    neighbours.put(n.getTypeId(), ids);
//                }
//            }
//            types.addAll(types_set);
//            type=types.get(random.nextInt(types.size()));
//            list=neighbours.get(type);
//            now=data.get(list.get(random.nextInt(list.size())));
//            path.add(now);
//        }
        return path;
    }

    public void generateEntitiesFeatureByTypes() {


        return;
    }

    public void generateSubPathsFromSamplings() {


        return;
    }
}
