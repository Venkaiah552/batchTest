import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
abstract class AbstractEntity<ID> : Persistable<ID> {
    @Transient
    private var isNew = true

    override fun isNew(): Boolean {
        return isNew
    }

    @PrePersist
    @PostLoad
    fun markNotNew() {
        isNew = false
    }
}

@Embeddable
data class EmployeeId(
        val companyId: String,
        val employeeNo: Long
): Serializable

@Entity
data class Employee(
        @EmbeddedId
        val employeeId: EmployeeId,
        val name: String,
        var dept: String

) : AbstractEntity<EmployeeId>() {
    /**
     * Returns the id of the entity.
     *
     * @return the id. Can be null.
     */
    override fun getId(): EmployeeId? {
        return employeeId
    }
}