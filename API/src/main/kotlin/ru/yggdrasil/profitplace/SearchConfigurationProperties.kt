package ru.yggdrasil.profitplace

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource

@PropertySource("classpath:search.properties")
@ConfigurationProperties(prefix = "search")
class SearchConfigurationProperties {
    var searchRadius: Int = 0
}