package renderer;


import geometries.Plane;
import org.junit.Test;
import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000);

        scene.geometries.add( //
                new Sphere(new Point3D(0, 0, -50), 50) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setNShininess(100).setKt(0.3)),
                new Sphere(new Point3D(0, 0, -50), 25) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(2500, 2500).setDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(new Point3D(-950, -900, -1000), 400) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20).setKt(0.5)),
                new Sphere(new Point3D(-950, -900, -1000), 200) //
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setNShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)), //
                new Sphere(new Point3D(60, 50, -50), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void trianglesTransparentSphere11() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.blue), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(100, -100, -135), new Point3D(50, 50, -100)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-130, 137, -140), new Point3D(0, 0, -140)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)), //
                new Sphere(new Point3D(60, 50, -50), 15).setEmission(new Color(java.awt.Color.cyan)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setNShininess(30).setKt(0.7)),
                new Triangle(new Point3D(90, 50, -50), new Point3D(60, 80, -50), new Point3D(60, 50, -20))
                        .setEmission(new Color(java.awt.Color.pink))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)),
                new Triangle(new Point3D(60, 50, -20), new Point3D(200, 100, -50), new Point3D(30, 100, -70))
                        .setEmission(new Color(java.awt.Color.red))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setNShininess(60)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(300, 200, 300), new Point3D(60, 50, 0)));

        ImageWriter imageWriter = new ImageWriter("refractionShadow11", 600, 600)
                .setMini_nX_nY(2,2);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene))
                .setMultithreading(3);

        render.renderImage();
        render.writeToImage();
    }



    @Test
    public void proTests2() {

        Scene scene = new Scene("Test my proj");
        Camera camera2 = new Camera(new Point3D(2, -400, 6), new Vector(0, 1, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(500, 500).setDistance(6000);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.gray), 0.15));
        scene.lights.add( //
                new DirectionalLight(new Color(700, 200, 200), new Vector(7, -50, 7)));
        scene.lights.add( //
                new DirectionalLight(new Color(100,100, 100),  new Vector(-1, -1, 0)));
        scene.lights.add( //
                new SpotLight(new Color(260, 180, 130), new Point3D(-100, -100, 150), new Vector(16, 6, -3)));

        scene.lights.add( //
                new PointLight(new Color(80, 60, 40), new Point3D(15, -520, 20)));

        scene.lights.add( //
                new SpotLight(new Color(260, 180, 130), new Point3D(100, 300, 150), new Vector(-16, 6, -3)));

        scene.lights.add( //
                new DirectionalLight(new Color(200,200, 200),  new Vector(1, -1, -1)));

        scene.lights.add( //
                new SpotLight(new Color(200, 200, 200), new Point3D(-100, 1500, 150), new Vector(6, -6, -6)));

        scene.geometries.add(
                new Plane(new Point3D(0, 0, 1), new Point3D(1, 1, 1), new Point3D(1, 0, 1))
                        .setMaterial(new Material().setKs(0.8).setKd(0).setNShininess(60)).setEmission(new Color(112,43,26)),
                new Plane(new Point3D(20, 0, 0), new Point3D(20, 1, 0), new Point3D(20, 1, 1))
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.2)));


        for (int i = 0; i < 1500; i+=40) {

            double x=getRandomNumber(-100,100);
            double y=getRandomNumber(0,3);

            scene.geometries
                    .add(new Triangle(
                            new Point3D(x, i, getRandomNumber(7,11)),
                            new Point3D(x+0.8, i, 1),
                            new Point3D(x-0.2,i, 1))
                            .setMaterial(new Material().setKd(0).setKs(0.7).setNShininess(0).setKr(0))
                            .setEmission(new Color(82,37,0)));

            scene.geometries
                    .add(
                            new Triangle(new Point3D(x, i, getRandomNumber(15,21)),
                                    new Point3D(x+y, i, 7),
                                    new Point3D(x-y, i, 7))
                                    .setMaterial(new Material().setKd(0).setKs(0.7).setNShininess(0).setKr(0))
                                    .setEmission(new Color(0,70,0)));
        }


        for (int i = -300; i < 2500; i+=5) {

            scene.geometries.add(new Sphere(new Point3D(getRandomNumber(-0.5,1)*i, i, getRandomNumber(1.7,10)), getRandomNumber(0.5,0.9)) //
                    .setEmission(new Color(getRandomNumber(0,200), getRandomNumber(0,200), getRandomNumber(0,200))) //
                    .setMaterial(new Material().setKd(0.8).setKs(0.5).setNShininess(30)));
        }



        Render render = new Render(). //
                setImageWriter(new ImageWriter("Balloon field_MyPro2", 400, 400)
                .setMini_nX_nY(2,2)) //
                .setCamera(camera2) //
                .setRayTracerBase(new RayTracerBasic(scene))
                .setMultithreading(3);
        render.renderImage();
        render.writeToImage();
    }

    public double getRandomNumber(double min, double max) {
        return  ((Math.random() * (max - min)) + min);
    }

}