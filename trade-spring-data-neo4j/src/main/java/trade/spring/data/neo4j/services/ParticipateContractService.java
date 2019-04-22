package trade.spring.data.neo4j.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trade.spring.data.neo4j.domain.relationship.ParticipateContract;
import trade.spring.data.neo4j.repositories.ContractRepository;
import trade.spring.data.neo4j.repositories.ParticipantContractRepository;

/**
 * Created by huangtao on 2019-04-22.
 */

@Service
public class ParticipateContractService {

    @Autowired
    private ParticipantContractRepository participantContractRepository;

    public ParticipateContract addParticipateContractRelationship(ParticipateContract participateContract) {
        return participantContractRepository.save(participateContract);
    }

}
