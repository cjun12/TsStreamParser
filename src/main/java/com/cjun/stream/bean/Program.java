package com.cjun.stream.bean;

public class Program {
    private int program_number;
    private int reserved;
    private int program_map_PID;

    public Program(int program_number, int reserved, int program_map_PID) {
        this.program_number = program_number;
        this.reserved = reserved;
        this.program_map_PID = program_map_PID;
    }

    @Override
    public String toString() {
        return "Program{" +
                "program_number=" + program_number +
                ", reserved=" + reserved +
                ", program_map_PID=" + program_map_PID +
                '}';
    }
}
