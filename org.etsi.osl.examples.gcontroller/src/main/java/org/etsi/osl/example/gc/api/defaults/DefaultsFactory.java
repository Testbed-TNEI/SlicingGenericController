package org.etsi.osl.example.gc.api.defaults;

import com.itav.slicemanager.models.Slice;
import com.itav.slicemanager.models.SliceN6ProtectionRule;
import com.itav.slicemanager.models.UeSliceAssociationCreateRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * Factory class for creating default instances with predefined values (for the default resource spec).
 */
public class DefaultsFactory {

    private DefaultsFactory() {
        /* This utility class should not be instantiated */
    }


    /**
     * Creates a default Slice instance for Resource Specification initialization, populated from the
     * {@code Slice} example in {@code docs/openapi_migrate_to.yaml}. {@code n6Protection} is seeded
     * from the {@code SliceN6ProtectionRule} example, since the {@code Slice} example itself omits it.
     *
     * @return default Slice instance
     */
    public static Slice getSlice() {
        Slice slice = new Slice();

        slice.setSliceId("AUTOMATICALLY_REPLACED_BY_GENERATED_OSL_RESOURCE_ID");
        slice.setCoverageArea(List.of(Slice.CoverageAreaEnum.IT));
        slice.setSst(1);
        slice.setSd("020202");
        slice.setDnn("embb");
        slice.setPriorityLabel(1);
        slice.setUeMobilityLevel(Slice.UeMobilityLevelEnum.NOMADIC);
        slice.setReliability(new BigDecimal("99.9"));
        slice.setDownlinkLatencyMs(15);
        slice.setUplinkLatencyMs(15);
        slice.setDownlinkGuaranteedBitratePerUeKbps(1000);
        slice.setUplinkGuaranteedBitratePerUeKbps(1000);
        slice.setDownlinkMaximumBitratePerUeKbps(20000);
        slice.setUplinkMaximumBitratePerUeKbps(20000);

        SliceN6ProtectionRule n6ProtectionRule = new SliceN6ProtectionRule();
        n6ProtectionRule.setType("firewall");
        n6ProtectionRule.setName("allow-video-ingest");
        n6ProtectionRule.setPriority(1);
        n6ProtectionRule.setServerIp("10.16.9.10");
        n6ProtectionRule.setMaskLen(32);
        n6ProtectionRule.setStartPort(443);
        n6ProtectionRule.setEndPort(443);
        n6ProtectionRule.setUplinkGuaranteedBitrateKbps(10000);
        n6ProtectionRule.setDownlinkGuaranteedBitrateKbps(50000);
        n6ProtectionRule.setUplinkMaximumBitrateKbps(20000);
        n6ProtectionRule.setDownlinkMaximumBitrateKbps(100000);
        n6ProtectionRule.setProtocol("tcp");
        n6ProtectionRule.setPassBlock("pass");
        slice.setN6Protection(List.of(n6ProtectionRule));

        return slice;
    }

    /**
     * Creates a default UeSliceAssociationCreateRequest instance for Resource Specification initialization,
     * populated from the {@code UeSliceAssociationCreateRequest} example in {@code docs/openapi_migrate_to.yaml}.
     *
     * @return default UeSliceAssociationCreateRequest instance
     */
    public static UeSliceAssociationCreateRequest getUeSliceAssociation() {
        UeSliceAssociationCreateRequest ueSliceAssociation = new UeSliceAssociationCreateRequest();

        ueSliceAssociation.setSliceId("REPLACE_FOR_SLICE_OSL_RESOURCE_ID");
        ueSliceAssociation.setSnssai("1-020202");
        ueSliceAssociation.setDnn("embb");
        ueSliceAssociation.setStaticIpv4Address("10.16.9.5");
        ueSliceAssociation.setAccessMobilityData(true);
        ueSliceAssociation.setUplinkAggregateMaximumBitrateKbps(1000);
        ueSliceAssociation.setDownlinkAggregateMaximumBitrateKbps(3000);

        return ueSliceAssociation;
    }

    /**
     * Default {@code ue_id} path/query parameter (an IMSI) — not part of {@code UeSliceAssociationCreateRequest}.
     */
    public static String getUeId() {
        return "999080100001134";
    }

}
