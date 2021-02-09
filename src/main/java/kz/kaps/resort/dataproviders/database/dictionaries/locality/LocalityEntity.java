package kz.kaps.resort.dataproviders.database.dictionaries.locality;

import kz.kaps.resort.dataproviders.database.ad.AdEntity;
import kz.kaps.resort.dataproviders.database.dictionaries.country.CountryEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "rsrt_dict_localities")
public class LocalityEntity {

    public static final String ATTRIBUTE_COUNTRY = "country";
    public static final String ATTRIBUTE_ID = "id";

    @Id
    @Column(name = "id_")
    @GeneratedValue
    private Long id;

    @Column(name = "name_ru_", nullable = false)
    private String nameRu;

    @Column(name = "name_en_", nullable = false)
    private String nameEn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id_", nullable = false)
    public CountryEntity country;

    @OneToMany(mappedBy = AdEntity.ATTRIBUTE_LOCALITY)
    private Set<AdEntity> ads = new HashSet<>();

    @Column(name = "image_file_name_with_ext_", nullable = false)
    private String imageFileNameWithExt;
}
