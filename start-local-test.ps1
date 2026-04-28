# Start local fEMR and run backup test

$femrPath = "c:\CodingProjects\Capstone\fEMR\femr"

Write-Host "🚀 Starting fEMR Local Testing" -ForegroundColor Cyan

# Change directory
Set-Location $femrPath

# Stop any running containers
Write-Host "`n🛑 Stopping existing containers..." -ForegroundColor Yellow
docker-compose down

# Start services
Write-Host "`n⬆️  Starting services (this may take 1-2 minutes)..." -ForegroundColor Yellow
docker-compose up -d

# Wait for MySQL to be ready
Write-Host "`n⏳ Waiting for MySQL to be healthy..." -ForegroundColor Yellow
$maxRetries = 30
$retries = 0
while ($retries -lt $maxRetries) {
    $health = docker-compose ps db | findstr "(healthy|running)" | measure-object | select-object -expandproperty Count
    if ($health -gt 0) {
        Write-Host "✓ MySQL is healthy" -ForegroundColor Green
        break
    }
    Start-Sleep -Seconds 2
    $retries++
    Write-Host "  Waiting... ($retries/$maxRetries)" -ForegroundColor Yellow
}

# Wait for fEMR to start
Write-Host "`n⏳ Waiting for fEMR to start..." -ForegroundColor Yellow
$maxRetries = 60
$retries = 0
while ($retries -lt $maxRetries) {
    $logs = docker-compose logs femr | findstr "Server started"
    if ($logs) {
        Write-Host "✓ fEMR started successfully" -ForegroundColor Green
        break
    }
    Start-Sleep -Seconds 2
    $retries++
    Write-Host "  Waiting... ($retries/$maxRetries)" -ForegroundColor Yellow
}

# Show summary
Write-Host "`n" -ForegroundColor Cyan
Write-Host "✅ Setup Complete!" -ForegroundColor Green
Write-Host "`n📊 Service Status:" -ForegroundColor Cyan
docker-compose ps

Write-Host "`n🌐 fEMR is available at:" -ForegroundColor Cyan
Write-Host "  http://localhost:8080" -ForegroundColor Green

Write-Host "`n📝 Next Steps:" -ForegroundColor Cyan
Write-Host "  1. Open http://localhost:8080 in your browser" -ForegroundColor White
Write-Host "  2. Login with your credentials" -ForegroundColor White
Write-Host "  3. Go to: Admin → Database → Backup" -ForegroundColor White
Write-Host "  4. Click 'Backup Your Data'" -ForegroundColor White
Write-Host "  5. Check logs: docker-compose logs femr | findstr DbDumpService" -ForegroundColor White

Write-Host "`n📖 View Logs:" -ForegroundColor Cyan
Write-Host "  docker-compose logs -f femr" -ForegroundColor Green

Write-Host "`n🛑 Stop Services:" -ForegroundColor Cyan
Write-Host "  docker-compose down" -ForegroundColor Green

Write-Host "`n"
