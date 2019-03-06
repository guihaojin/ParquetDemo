package com.guihaojin.demo;

import static org.apache.parquet.hadoop.ParquetFileWriter.Mode.OVERWRITE;
import static org.apache.parquet.hadoop.metadata.CompressionCodecName.SNAPPY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.reflect.ReflectData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Team> teams = new ArrayList<>();

        Team team1 = new Team("A");
        team1.addMember(new Person("Alice", 1));
        team1.addMember(new Person("Amy", 3));

        Team team2 = new Team("B");
        team2.addMember(new Person("Bob", 20));
        team2.addMember(new Person("Blare", 14));
        team2.addMember(new Person("Beep", 5));

        teams.add(team1);
        teams.add(team2);

        Path dataFile = new Path("/tmp/demo.snappy.parquet");

        // Write as Parquet file.
        try (ParquetWriter<Team> writer = AvroParquetWriter.<Team>builder(dataFile)
                .withSchema(ReflectData.AllowNull.get().getSchema(Team.class))
                .withDataModel(ReflectData.get())
                .withConf(new Configuration())
                .withCompressionCodec(SNAPPY)
                .withWriteMode(OVERWRITE)
                .build()) {

            for (Team team : teams) {
                writer.write(team);
            }
        }

        // Read from Parquet file.
        try (ParquetReader<Team> reader = AvroParquetReader.<Team>builder(dataFile)
                .withDataModel(new ReflectData(Team.class.getClassLoader()))
                .disableCompatibility()
                .withConf(new Configuration())
                .build()) {
            Team team;

            while ((team = reader.read()) != null) {
                System.out.println(team);
            }
        }
    }
}
