package com.song.demo.Service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.QueryDevicesByAccountRequest;
import com.aliyuncs.push.model.v20160801.QueryDevicesByAccountResponse;
import com.aliyuncs.push.model.v20160801.QueryPushListRequest;
import com.aliyuncs.push.model.v20160801.QueryPushListResponse;
import com.song.demo.Service.AliasesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: AliasesServiceImpl
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/22 15:08
 * @Version: 1.0
 **/
@Slf4j
@Service
public class AliasesServiceImpl implements AliasesService {

    @Override
    public void query() throws Exception {
        QueryDevicesByAccountRequest accountRequest=new QueryDevicesByAccountRequest();
        accountRequest.setAppKey(25747201l);
        accountRequest.setAccount("aNd9C11vQweHoZ2MW7-owQ");
        DefaultAcsClient client = createClient();
        QueryDevicesByAccountResponse acsResponse = client.getAcsResponse(accountRequest);
        List<String> deviceIds = acsResponse.getDeviceIds();
        log.info("#####"+deviceIds);
        for (String deviceId : deviceIds) {
            System.out.println("==="+deviceId);
        }
    }

    @Override
    public void queryRecords() throws Exception{
        QueryPushListRequest request=new QueryPushListRequest();
        request.setAppKey(25747201l);
        request.setStartTime("2019-06-01T00:00:00Z");
        request.setEndTime("2019-06-22T00:00:00Z");
        request.setPushType("MESSAGE");
        request.setPage(1);
        request.setPageSize(20);

        DefaultAcsClient client = createClient();
        QueryPushListResponse acsResponse = client.getAcsResponse(request);
        List<QueryPushListResponse.PushMessageInfo> pushMessageInfos = acsResponse.getPushMessageInfos();
        for (QueryPushListResponse.PushMessageInfo pushMessageInfo : pushMessageInfos) {
            System.out.println(pushMessageInfo.getMessageId()+">>>"+pushMessageInfo.getBody());
        }
    }


    private DefaultAcsClient createClient(){
        IClientProfile profile1 = DefaultProfile.getProfile("cn-hangzhou", "LTAIX50qWcoAx5ei", "qO7xcGys4qb6uFyrtjylikcPWFKpbP");
        DefaultAcsClient client1 = new DefaultAcsClient(profile1);
        return client1;
    }

}
