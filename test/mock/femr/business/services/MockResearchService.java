package mock.femr.business.services;

import femr.common.dto.ServiceResponse;
import femr.common.models.ResearchFilterItem;
import femr.ui.models.research.json.ResearchGraphDataItem;


public class MockResearchService {

    public ServiceResponse<ResearchGraphDataItem> getGraphData(ResearchFilterItem filters){

        ResearchGraphDataItem graphDataItem = new ResearchGraphDataItem();

        if( filters.getPrimaryDataset().equals("age") ) {

            graphDataItem.setAverage(1.0f);
            graphDataItem.setMedian(1.0f);

        }
        else if( filters.getPrimaryDataset().equals("gender") ){



        }



        ServiceResponse<ResearchGraphDataItem> response = new ServiceResponse<>();
        response.setResponseObject(graphDataItem);

        return response;
    }


}
