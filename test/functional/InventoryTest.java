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
        public static final String configFilePath = "./Users/Olesh/Documents/SchoolArchives/CSC4111/femr/conf/application.dev.conf";

        @Before
        public void setup() {
//            Module testModule = new AbstractModule() {
//                @Override
//                public void configure() {
//                    // Install custom test binding here
//                }
//            };
//
//            GuiceApplicationBuilder builder = new GuiceApplicationLoader()
//                    .builder(new Context(Environment.simple()))
//                    .overrides(testModule);
//            Guice.createInjector(builder.applicationModule()).injectMembers(this);
//            application = fakeApplication(inMemoryDatabase("femr"));
//            application = new GuiceApplicationBuilder()
//                    .loadConfig(ConfigFactory.parseFile(new File(configFilePath)))
//                    .configure(ConfigFactory.parseMap(Helpers.inMemoryDatabase()))//Config.parseMap(Helpers.inMemoryDatabase())
//                    .in(Mode.TEST)
////                    .in(classLoader)
//                    .build();
//            Helpers.start(application);
//            Module testModule = new AbstractModule() {
//                @Override
//                public void configure() {
//                    // Install custom test binding here
//                }
//            };

//            GuiceApplicationBuilder builder = new GuiceApplicationLoader()
//                    .builder(new Context(Environment.simple()))
//                    .overrides(testModule);
//            Guice.createInjector(builder.applicationModule()).injectMembers(this);
//            Database database =
//            );
//            Database d = Databases.inMemory(
//                    "femr",
//                    ImmutableMap.of("MODE", "MYSQL"),
//                    ImmutableMap.of("logStatements", true)
//
////                        ImmutableMap.of(
////                                "MODE", "MYSQL"
////                        ),
////                        ImmutableMap.of(
////                                "logStatements", true
////                        )
//            );
//            Database d = Databases.inMemoryDatabase("femr",
//                    ImmutableMap.of("Mode", "MYSQL"),
//                    ImmutableMap.of("logStatements", true));

            try {
                //#apply-evolutions-custom-path
//                Evolutions.applyEvolutions(database,
//                        Evolutions.fromClassLoader(
//                                getClass().getClassLoader(), "testdatabase/")
//                );
                Map<String, String> optionMap = new HashMap<String, String>();
                optionMap.put("MODE", "MYSQL");
//                optionMap.put("log")
                application = fakeApplication(
//                        d
                        Helpers.inMemoryDatabase("femr", optionMap)
                );
                Helpers.start(application);
                //#apply-evolutions-custom-path
            } finally {
//                d.shutdown();
            }


        }

        @Test
        public void testtest() {
            System.out.println("\033[42m AAAAAAAAAAAAAAAAAAAAA \033[0m");
        }

        @After
        public void teardown() {
            Helpers.stop(application);
        }
}
