package kz.kaps.resort.dataproviders.database.dictionaries.country;


import kz.kaps.resort.dataproviders.database.dictionaries.locality.LocalityEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "rsrt_dict_countries")
public class CountryEntity {

    @Id
    @Column(name = "id_")
    @GeneratedValue
    private Long id;

    @Column(name = "name_ru_")
    private String nameRu;

    @Column(name = "name_en_")
    private String nameEn;

    @OneToMany(mappedBy = "country")
    private List<LocalityEntity> localities;

}
