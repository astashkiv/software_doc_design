package com.stashkiv.pharmacy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.stashkiv.pharmacy.web.rest.TestUtil;

public class DoctorCertificateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoctorCertificate.class);
        DoctorCertificate doctorCertificate1 = new DoctorCertificate();
        doctorCertificate1.setId(1L);
        DoctorCertificate doctorCertificate2 = new DoctorCertificate();
        doctorCertificate2.setId(doctorCertificate1.getId());
        assertThat(doctorCertificate1).isEqualTo(doctorCertificate2);
        doctorCertificate2.setId(2L);
        assertThat(doctorCertificate1).isNotEqualTo(doctorCertificate2);
        doctorCertificate1.setId(null);
        assertThat(doctorCertificate1).isNotEqualTo(doctorCertificate2);
    }
}
