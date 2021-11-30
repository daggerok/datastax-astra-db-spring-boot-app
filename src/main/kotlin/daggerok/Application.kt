package daggerok

/*
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.ResultSet
import com.datastax.oss.driver.api.core.cql.Row
*/
import java.nio.file.Path
import java.util.UUID
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.core.mapping.CassandraType
import org.springframework.data.cassandra.core.mapping.CassandraType.Name
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication/*(
    exclude = [
        CassandraAutoConfiguration::class,
        CassandraDataAutoConfiguration::class,
        CassandraRepositoriesAutoConfiguration::class,
        CassandraReactiveDataAutoConfiguration::class,
        CassandraReactiveRepositoriesAutoConfiguration::class,
        CassandraHealthContributorAutoConfiguration::class,
        CassandraReactiveHealthContributorAutoConfiguration::class,
    ]
)*/
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
@EnableConfigurationProperties(DatastaxProps::class)
class DatastaxPropsConfig

@ConstructorBinding
@ConfigurationProperties("datastax.astra")
class DatastaxProps(
    val secureConnectBundle: Path,
    val clientId: String,
    val clientSecret: String,
    val keyspace: String,
)

@Configuration
class AstraDbConfig(private val props: DatastaxProps) {

    @Bean
    fun cqlSessionBuilderCustomizer() =
        CqlSessionBuilderCustomizer {
            it.withCloudSecureConnectBundle(props.secureConnectBundle)
                .withAuthCredentials(props.clientId, props.clientSecret)
                .withKeyspace(props.keyspace)
        }

    /*
    @Bean(destroyMethod = "close")
    fun cqlSession(): CqlSession =
        CqlSession
            .builder()
            .withCloudSecureConnectBundle(props.secureConnectBundle)
            .withAuthCredentials(props.clientId, props.clientSecret)
            .withKeyspace(props.keyspace)
            .build().also { session ->
                // Select the release_version from the system.local table:
                val rs: ResultSet = session.execute("SELECT release_version FROM system.local")
                val row: Row? = rs.one()
                //Print the results of the CQL query to the console:
                if (row != null) println(row.getString("release_version"))
                else println("An error occurred.")
            }
    */
}

@Table("messages")
data class Message(

    @PrimaryKey
    @CassandraType(type = Name.UUID)
    val uuid: UUID = UUID.randomUUID(),

    @CassandraType(type = Name.BLOB)
    val content: String = "",
)

interface Messages : CassandraRepository<Message, UUID>

@RestController
class MessagesResource(private val messages: Messages) {

    @PostMapping("/api/v1/messages")
    fun getMessages(@RequestBody message: Message): Message =
        messages.save(message)

    @GetMapping("/api/v1/messages")
    fun getMessages(): Iterable<Message> =
        messages.findAll()
}
