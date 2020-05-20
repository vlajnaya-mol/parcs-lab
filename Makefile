all: run

clean:
	rm -f out/Integration.jar out/AdaptiveQuadrature.jar

out/Integration.jar: out/parcs.jar src/Integration.java
	@javac -cp out/parcs.jar src/Integration.java
	@jar cf out/Integration.jar -C src Integration.class
	@rm -f src/Integration.class

out/AdaptiveQuadrature.jar: out/parcs.jar src/AdaptiveQuadrature.java
	@javac -cp out/parcs.jar src/AdaptiveQuadrature.java
	@jar cf out/AdaptiveQuadrature.jar -C src AdaptiveQuadrature.class
	@rm -f src/AdaptiveQuadrature.class

build: out/Integration.jar out/AdaptiveQuadrature.jar

run: out/Integration.jar out/AdaptiveQuadrature.jar
	@cd out && java -cp 'parcs.jar:Integration.jar' Integration
