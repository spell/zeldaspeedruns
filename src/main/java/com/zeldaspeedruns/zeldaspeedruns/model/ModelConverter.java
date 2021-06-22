package com.zeldaspeedruns.zeldaspeedruns.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface ModelConverter<Model extends AbstractModel, Representation> {
    Model from(Representation representation);

    Representation from(Model model);

    default List<Representation> fromModels(final Collection<Model> models) {
        return models.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }

    default List<Model> fromRepresentation(final Collection<Representation> representations) {
        return representations.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }
}
