package com.fastcampus.projectboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {

    private final PaginationService sut;


    public PaginationServiceTest(@Autowired PaginationService sut) {
        this.sut = sut;
    }

    @DisplayName("현재 페이지, 번호를 주면 | 페이징 바를 만들어준다.")
    @ParameterizedTest(name = "[{index}] 현재 페이지: {0}, 총 페이지: {1} => {2}")
    @MethodSource("givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers")
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer> expected){
        //given

        //when
        List<Integer> actual= sut.getPaginationBarNumbers(currentPageNumber,totalPages);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(){
        return Stream.of(
                arguments(0, 13, List.of(0, 1, 2, 3, 4)),
                arguments(1, 13, List.of(0, 1, 2, 3, 4)),
                arguments(2, 13, List.of(0, 1, 2, 3, 4)),
                arguments(3, 13, List.of(1, 2, 3, 4, 5)),
                arguments(4, 13, List.of(2, 3, 4, 5, 6)),
                arguments(5, 13, List.of(3, 4, 5, 6, 7)),
                arguments(6, 13, List.of(4, 5, 6, 7, 8)),
                arguments(10, 13, List.of(8, 9, 10, 11, 12)),
                arguments(11, 13, List.of(9, 10, 11, 12)),
                arguments(12, 13, List.of(10, 11, 12))
                        );
    }

    @DisplayName("현재 페이지, 번호를 주면 | 페이징 바를 만들어준다.")
    @Test
    void given_whenWantsCurrentBar_thenReturnLength(){
        //given

        //when
        int barLength=sut.currentBarLength();

        //then
        assertThat(barLength).isEqualTo(5);

    }

}