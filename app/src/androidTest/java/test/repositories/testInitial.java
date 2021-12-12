package test.repositories;


//import android.support.test.InstrumentationRegistry;

import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;

import model.room.entity.Humidity;
import model.room.entity.Precipitation;
import model.room.entity.Temperature;
import model.room.repositories.HumidityRepo;
import model.room.repositories.PrecipitationRepo;
import model.room.repositories.TemperatureRepo;

public class testInitial {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();
    HumidityRepo repository;
    Observer<List<Humidity>> observer;
    List<Humidity> list;

    TemperatureRepo repository2;
    Observer<List<Temperature>> observer2;
    List<Temperature> list2;

    PrecipitationRepo repository3;
    Observer<List<Precipitation>> observer3;
    List<Precipitation> list3;

    @Before
    public void setUp() throws Exception {
        observer = new Observer<List<Humidity>>() {
            @Override
            public void onChanged(List<Humidity> obj) {
                Log.d("WORKING?","IM TRYING TO DO SOMETHING");
                list = obj;
                if (obj.isEmpty() == false){
                    System.out.println("IM TRYING TO DO SOMETHING V 2"+obj.get(0).getValue());
                }
                //System.out.println("IM TRYING TO DO SOMETHING V 2"+obj.get(0).getValue());
            }
        };
        observer2 = new Observer<List<Temperature>>() {
            @Override
            public void onChanged(List<Temperature> obj) {

                list2 = obj;
            }
        };
        observer3 = new Observer<List<Precipitation>>() {
            @Override
            public void onChanged(List<Precipitation> obj) {

                list3 = obj;
            }
        };
        //Context context = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        repository = new HumidityRepo(ApplicationProvider.getApplicationContext());
        repository2 = new TemperatureRepo(ApplicationProvider.getApplicationContext());
        repository3 = new PrecipitationRepo(ApplicationProvider.getApplicationContext());

    }

    @Test
    public void populateAccCustomerAPI() throws InterruptedException {
        repository.getHumidityList().observeForever(observer);
        repository2.getTemperatureList().observeForever(observer2);
        repository3.getPrecipitationList().observeForever(observer3);


        repository.getHumidity("Horsens");
        Thread.sleep(30000);
        Assert.assertEquals(false,list.isEmpty());

        repository2.getTemperature("Aarhus");
        Thread.sleep(20000);
        Assert.assertEquals(false,list2.isEmpty());

        repository3.getPrecipitation("Horsens");
        Thread.sleep(20000);
        Assert.assertEquals(false,list3.isEmpty());



        System.out.println("WORKING? final "+list.get(0).getValue());
        System.out.println("WORKING? final "+list.get(0).getID());

        System.out.println("WORKING? final "+list3.get(0).getID());
        System.out.println("WORKING????????final "+list3.get(0).getValue());
       Log.d("WORKING!!!!!!!!!!",list2.get(0).getValue().toString());
       // Log.d("WORKING??????????",list3.get(0).getValue());


    }
}