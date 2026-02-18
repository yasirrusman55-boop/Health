// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorPos",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapacitorPos",
            targets: ["CapacitorPosPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "CapacitorPosPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/CapacitorPosPlugin"),
        .testTarget(
            name: "CapacitorPosPluginTests",
            dependencies: ["CapacitorPosPlugin"],
            path: "ios/Tests/CapacitorPosPluginTests")
    ]
)