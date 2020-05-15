package com.stashkiv.pharmacy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.stashkiv.pharmacy.web.rest.TestUtil;

public class PatientCertificateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientCertificate.class);
        PatientCertificate patientCertificate1 = new PatientCertificate();
        patientCertificate1.setId(1L);
        PatientCertificate patientCertificate2 = new PatientCertificate();
        patientCertificate2.setId(patientCertificate1.getId());
        assertThat(patientCertificate1).isEqualTo(patientCertificate2);
        patientCertificate2.setId(2L);
        assertThat(patientCertificate1).isNotEqualTo(patientCertificate2);
        patientCertificate1.setId(null);
        assertThat(patientCertificate1).isNotEqualTo(patientCertificate2);
    }
}
