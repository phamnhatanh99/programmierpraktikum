package de.tukl.programmierpraktikum2021.p2.a2;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class PacmanExtendedTest {
    @Test
    public void Test() throws IOException {
        PacmanExtendedImpl pac= new PacmanExtendedImpl();
        System.out.println(pac.getCyc());
    }
}
