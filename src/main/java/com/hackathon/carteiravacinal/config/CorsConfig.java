package com.hackathon.carteiravacinal.config;

import spark.Spark;

public class CorsConfig {

    public static void configurarCORS() {
        Spark.before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
            res.type("application/json");
        });

        Spark.options("/*", (req, res) -> {
            res.status(200);
            return "OK";
        });
    }
}
