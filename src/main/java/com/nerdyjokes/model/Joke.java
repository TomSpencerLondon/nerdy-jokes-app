package com.nerdyjokes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
public class Joke {

    private final String type;
    private final Value value;

    @Data
    @RequiredArgsConstructor
    @NoArgsConstructor(access = PRIVATE, force = true)
    public static class Value {

        private final int id;
        private final String joke;
        private final List<String> categories;
    }
}
