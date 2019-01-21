package backery;

import backery.exception.ProductNotFound;

import java.util.*;
import java.util.stream.Collectors;


public class Baker {

    final Map<String, List<IProduct>> productMap = new HashMap<String, List<IProduct>>();

    public Baker(IProduct... products) {

        Arrays.asList(products).stream().forEach(product -> {
            List<IProduct> iProduct = productMap.get(product.getCode());
            if (iProduct == null) {
                iProduct = new ArrayList<>();
                productMap.put(product.getCode(), iProduct);
            }
            iProduct.add(product);
        });

    }


    public ShippingBag getPacks(final String productCode, int quantity) throws ProductNotFound {
        List<IProduct> productList = productMap.getOrDefault(productCode, new ArrayList<>());

        if (productList.isEmpty())
            throw new ProductNotFound();

        List<Package> collectedPackageList =
                productList.stream()
                        .filter(product -> product instanceof Package)
                        .map(product -> (Package) product)
                        .collect(Collectors.toList());

        List<PackageChain> paths = new ArrayList<>();
        paths.add(new PackageChain(Arrays.asList(quantity)));

        PackageChain minimalPkg = getMinimalPkg(collectedPackageList, paths);
        List<IProduct> packages = new ArrayList<>();


        if(minimalPkg==null) return new ShippingBag(packages);

        List<Integer> numbersList = minimalPkg.getNumbersList();

        List<Integer> pkgQuantityList = new ArrayList<>();
        for(int i=0;i<numbersList.size()-1;i++){
            System.out.println("Numbers "+numbersList.get(i));
            pkgQuantityList.add(numbersList.get(i)-numbersList.get(i+1));
        }

        for(int index=0;index<pkgQuantityList.size();index++){
            Integer pkgQuantity = pkgQuantityList.get(index);
            Optional<Package> optionalPackage =
                    collectedPackageList.stream().
                            filter(aPackage -> aPackage.getQuantity() == pkgQuantity)
                    .findFirst();
            packages.add(optionalPackage.get());
        }
        return new ShippingBag(packages);
    }

    private PackageChain getMinimalPkg(List<Package> collectedPackageList, List<PackageChain> paths) {

        List<PackageChain> newPackagePaths = new ArrayList<>();

         for(Package pkg : collectedPackageList){

             for(PackageChain packageChain : paths){
                 int lastNumber = packageChain.getLastNumber();
                 int newNumber = lastNumber-pkg.getQuantity();

                 if(newNumber>=0){
                     PackageChain packagePath = new PackageChain(packageChain);
                     packagePath.add(newNumber);
                     newPackagePaths.add(packagePath);
                     if(newNumber==0){
                         return packagePath;
                     }
                 }
             }
        }

        if (newPackagePaths.size() == 0) {
            return null;
        }

        return getMinimalPkg(collectedPackageList,newPackagePaths);

    }

    /***
     *
     * @param productCode
     * @param quantity
     * @return
     * @throws ProductNotFound
     */
    public ShippingBag getPacks1(final String productCode, int quantity) throws ProductNotFound {

        List<IProduct> productList = productMap.getOrDefault(productCode, new ArrayList<>());

        if (productList.isEmpty())
            throw new ProductNotFound();

        List<IProduct> collectedPackageList =
                productList.stream()
                        .filter(product -> product instanceof Package)
                        .map(product -> (Package) product)
                        .sorted((o1, o2) -> o2.getQuantity() - o1.getQuantity())
                        .map(aPackage -> (IProduct) aPackage)
                        .collect(Collectors.toList());


        List<IProduct> packages = new ArrayList<>();
        List<IProduct> subPackages = new ArrayList<>();

        int desiredQuantity = quantity;
        int size = collectedPackageList.size();
        Package pkg = null;

        for (int i = 0; i < size; i++) {
            pkg = (Package) collectedPackageList.get(i);
            int noOfItemsForAPkg = desiredQuantity / pkg.getQuantity();
            desiredQuantity = desiredQuantity % pkg.getQuantity();
            addNumberOfItems(packages, pkg, noOfItemsForAPkg);
            if (desiredQuantity == 0)
                break;


            for (int k = i; k < size; k++) {

                for (int j = k + 1; j < size; j++) {
                    pkg = (Package) collectedPackageList.get(j);
                    noOfItemsForAPkg = desiredQuantity / pkg.getQuantity();
                    desiredQuantity = desiredQuantity % pkg.getQuantity();
                    addNumberOfItems(subPackages, pkg, noOfItemsForAPkg);
                    if (desiredQuantity == 0)
                        break;
                    if (j == size - 1 && desiredQuantity != 0) {
                        desiredQuantity = quantity % ((Package) collectedPackageList.get(i)).getQuantity();
                        subPackages.clear();
                    }
                }
                if (k == size - 1 && desiredQuantity != 0) {
                    desiredQuantity = quantity;
                    packages.clear();
                }


            }
        }

        packages.addAll(subPackages);
        return new ShippingBag(packages);
    }

    private void addNumberOfItems(List<IProduct> packages, Package pkg, int noOfItemsForAPkg) {
        for (int j = 0; j < noOfItemsForAPkg; j++)
            packages.add(pkg);
    }

}
