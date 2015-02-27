//package mock.femr.business.services;
//
//import femr.common.dto.ServiceResponse;
//import femr.common.models.ResearchFilterItem;
//import femr.ui.models.research.json.ResearchGraphDataModel;
//
//
//public class MockResearchService {
//
//    public ServiceResponse<ResearchGraphDataModel> getGraphData(ResearchFilterItem filters){
//
//        ResearchGraphDataModel graphDataItem = new ResearchGraphDataModel();
//
//        if( filters.getPrimaryDataset().equals("age") ) {
//
//            graphDataItem.setAverage(1.0f);
//            graphDataItem.setMedian(1.0f);
//
//        }
//        else if( filters.getPrimaryDataset().equals("gender") ){
//
//
//
//        }
//
//
//
//        ServiceResponse<ResearchGraphDataModel> response = new ServiceResponse<>();
//        response.setResponseObject(graphDataItem);
//
//        return response;
//    }
//
//
//}
