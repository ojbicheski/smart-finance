package com.smartfinance.loader.config;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JSEngineConfig {
  @Bean
  public Context.Builder jsContext() {
    log.info("Loading ScriptEngine 'js.ecmascript-version: 2022'");
    return Context.newBuilder("js")
            .allowHostClassLoading(true)
            .allowNativeAccess(true)
            .allowPolyglotAccess(PolyglotAccess.ALL)
            .allowHostAccess(HostAccess.ALL)
            .allowHostClassLookup(s -> true)
            .option("js.ecmascript-version", "2022");
  }
}
