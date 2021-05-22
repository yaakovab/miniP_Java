package primitives;

/**
 * this class represents the kind of material the object made of
 */
public class Material {
    // diffuse attenuation factor
    public double kD;
    // specular attenuation factor
    public double kS;
    // shininess of material
    public int nShininess;
    /**
     *  reflection
     */
    public double kr = 0;
    /**
     * transparency (refraction)
     */
    public double kt = 0;

    /**
     * default constructor
     */
    public Material() {
        kD = 0;
        kS = 0;
        nShininess = 0;
    }

    //***************setters***************//
    /**
     * use builder pattern
     * @param kD diffuse attenuation factor
     * @return this for chaining purpose
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * use builder pattern
     * @param kS specular attenuation factor
     * @return this for chaining purpose
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * use builder pattern
     * @param nShininess shininess of material
     * @return this for chaining purpose
     */
    public Material setNShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Material setKt (double kt) {
        this.kt = kt;
        return this;
    }

    public Material setKr (double kr) {
        this.kr = kr;
        return this;
    }
}
