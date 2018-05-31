package functional;

import com.google.inject.Inject;

//import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import play.Application;
import play.Environment;
import play.inject.guice.GuiceApplicationBuilder;

import java.io.File;
import java.util.Map;
import java.util.HashMap;

import play.db.evolutions.*;

import play.test.*;
import play.Mode;
import play.Configuration;
//import play.db.*;
import play.db.Database;
import play.api.db.Databases;
import play.api.*;
import play.db.evolutions.Evolutions;
import static play.test.Helpers.*;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import com.google.inject.Module;
import com.google.inject.AbstractModule;

import com.google.common.collect.ImmutableMap;



public class InventoryTest{
        @Inject Application application;

        @Before
        public void setup() {
            Map<String, String> h2OptionMap = new HashMap<String, String>();
            h2OptionMap.put("MODE", "MYSQL");
            application= fakeApplication( Helpers.inMemoryDatabase("femr", h2OptionMap));
            Helpers.start(application);
        }

        @Test
        public void testtest() {
            System.out.println("[\033[42m  \033[0m] Yay, Setup ran! ");
        }

        @After
        public void teardown() {
            Helpers.stop(application);
        }
}
