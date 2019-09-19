package com.aura.bdp22.g3.flume;

import org.apache.flume.Event;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FlumeAvroTest {

    public static void main(String[] args) throws Exception{

        RpcClient client = RpcClientFactory.getDefaultInstance("hadoopnode",41414);

        Event event = EventBuilder.withBody("hello, avro flume", Charset.forName("UTF-8"));

        List<Event> events = new ArrayList<Event>();

        events.add(event);
        client.appendBatch(events);
        client.close();
    }

}
