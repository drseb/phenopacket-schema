package org.phenopackets.schema.v1;

import org.junit.jupiter.api.Test;
import org.phenopackets.schema.v1.core.*;
import org.phenopackets.schema.v1.examples.TestExamples;

import java.io.IOException;

import static org.phenopackets.schema.v1.PhenoPacketTestUtil.ontologyClass;
import static org.phenopackets.schema.v1.io.PhenoPacketFormat.toYaml;

/**
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 */
public class PhenoPacketTest {

    @Test
    public void testRareDiseaseDiagnosis() throws Exception {

        Gene fgfr2Gene = Gene.newBuilder()
                .setId("HGNC:3689")
                .setSymbol("FGFR2")
                .build();

        Variant pathogenicVariant = Variant.newBuilder()
                .setSequence("NC_000010.10")
                .setPosition(123256214)
                .setDeletion("T")
                .setInsertion("G")
                .putSampleGenotypes("proband", ontologyClass("GENO:0000135", "heterozygous"))
                .build();

        Phenotype coronalCraniosynostosis = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0004440", "Coronal craniosynostosis"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();

        Phenotype maxillaryHypoplasia = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000327", "Maxillary hypoplasia"))
                .build();

        Phenotype cloverleafSkullOccasional = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0002676", "Cloverleaf skull"))
                .addModifiers(ontologyClass("HP:0040283", "Occasional"))
                .build();

        Phenotype brachydactyly = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001156", "Brachydactyly"))
                .build();

        Phenotype craniosynostosis = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001363", "Craniosynostosis"))
                .build();

        Phenotype broadThumb = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0011304", "Broad thumb"))
                .build();

        Phenotype broadHallux = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0010055", "Broad hallux"))
                .setClassOfOnset(ontologyClass("HP:0011463", "Childhood onset"))
                .build();

        Phenotype proptosisCongenitalSevere = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000520", "Proptosis"))
                .addModifiers(ontologyClass("HP:0012828", "Severe"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Phenotype proptosisCongenitalMild = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0000520", "Proptosis"))
                .addModifiers(ontologyClass("HP:0012825", "Mild"))
                .setClassOfOnset(ontologyClass("HP:0003577", "Congenital onset"))
                .build();

        Phenotype intellectualDisabilityOccasional = Phenotype.newBuilder()
                .setType(ontologyClass("HP:0001249", "Intellectual disability"))
                .addModifiers(ontologyClass("HP:0040283", "Occasional"))
                .build();

        Disease pfeifferSyndrome = Disease.newBuilder()
                .setId("OMIM:101600")
                .setLabel("PFEIFFER SYNDROME")
                .build();


        Individual proband = Individual.newBuilder()
                .setId("proband")
                .addPhenotypes(brachydactyly)
                .addPhenotypes(craniosynostosis)
                .addPhenotypes(broadThumb)
                .addPhenotypes(broadHallux)
                .addPhenotypes(proptosisCongenitalMild)
                .build();

        MetaData metaData = MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2018-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/GENO_")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("so")
                        .setName("Sequence types and features")
                        .setNamespacePrefix("SO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/SO_")
                        .setUrl("http://purl.obolibrary.org/obo/so.owl")
                        .setVersion("2015-11-24")
                        .build())
                .setCreatedBy("Jules J.")
                .build();

        PhenoPacket pfeifferDiagnosisExample = PhenoPacket.newBuilder()
                .setSubject(proband)
                .addVariants(pathogenicVariant)
                .addGenes(fgfr2Gene)
                .addDiseases(pfeifferSyndrome)
                .addHtsFiles(HtsFile.newBuilder()
                        .setHtsFormat(HtsFile.HtsFormat.VCF)
                        .setGenomeAssembly(GenomeAssembly.GRCH_37)
                        // in this case the proband identifier is different to the sample
                        // identifier used in the VCF file
                        .putIndividualToSampleIdentifiers(proband.getId(), "SAMPLE0001")
                        .setFile(File.newBuilder().setPath("/data/vcfs/proband.vcf").build())
                        .build())
                .setMetaData(metaData)
                .build();

        System.out.println(toYaml(pfeifferDiagnosisExample));
    }

    @Test
    void printCancer() throws IOException {
        System.out.println(toYaml(TestExamples.cancerPhenoPacket()));
    }

    @Test
    void printRareDisease() throws IOException {
        System.out.println(toYaml(TestExamples.rareDiseasePhenoPacket()));
    }

    @Test
    void printBiosamples() throws IOException {
        System.out.println(toYaml(TestExamples.biosamplesPhenoPacket()));
    }
}
