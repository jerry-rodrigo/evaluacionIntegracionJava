package com.desafioTecnico.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Esta clase contiene pruebas unitarias para la clase PriceService.
 * Utiliza Mockito para simular el comportamiento del repositorio de precios y realizar pruebas de los métodos de PriceService.
 */
public class PriceServiceTest {
    @Mock
    private IPriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Prueba unitaria para el método getPriceByParameters de PriceService.
     * Verifica que el método getPriceByParameters devuelve una lista de precios correctamente.
     */
    @Test
    public void getPriceByParameters_Success() {
        List<Price> mockPrices = new ArrayList<>();
        Price price = new Price();
        mockPrices.add(price);

        when(priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(any(), any(), any(), any()))
                .thenReturn(mockPrices);

        List<Price> result = priceService.getPriceByParameters(1L, 35455L, new Date());

        assertEquals(1, result.size());
        assertEquals(price, result.get(0));
    }
}
