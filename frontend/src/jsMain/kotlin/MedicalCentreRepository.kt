import dev.fritz2.binding.RootStore
import dev.fritz2.lenses.IdProvider
import dev.fritz2.resource.Resource
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import model.MedicalCentreDTO

object MedicalCentreRepository: RootStore<List<MedicalCentreDTO>> (emptyList(), id = "medicalCentres") {

    object MedicalCentreResource : Resource<MedicalCentreDTO, Long> {
        override val idProvider: IdProvider<MedicalCentreDTO, Long> = MedicalCentreDTO::id

        override fun deserialize(source: String) = Json.decodeFromString(MedicalCentreDTO.serializer(), source)
        override fun serialize(item: MedicalCentreDTO): String = Json.encodeToString(MedicalCentreDTO.serializer(), item)
        override fun deserializeList(source: String): List<MedicalCentreDTO> = Json.decodeFromString(ListSerializer(MedicalCentreDTO.serializer()), source)
        override fun serializeList(items: List<MedicalCentreDTO>): String = Json.encodeToString(ListSerializer(MedicalCentreDTO.serializer()), items)
    }
}