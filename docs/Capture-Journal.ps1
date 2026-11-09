param(
    [Parameter(Mandatory = $true)][string]$Journal,
    [Parameter(Mandatory = $true)][string]$Image,
    [string]$Titre = 'Journal Maven',
    [int]$PremiereLigne = 0,
    [int]$NombreLignes = 42
)

# Capture du contrôle Windows affichant le journal réel, sans modifier ses résultats.
Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing
$lignes = @(Get-Content -LiteralPath $Journal | Select-Object -Skip $PremiereLigne -First $NombreLignes)
$form = New-Object System.Windows.Forms.Form
$form.Text = $Titre
$form.ClientSize = New-Object System.Drawing.Size(1420, (70 + 22 * $lignes.Count))
$form.BackColor = [System.Drawing.Color]::FromArgb(20, 25, 34)
$titreVue = New-Object System.Windows.Forms.Label
$titreVue.Text = $Titre
$titreVue.ForeColor = [System.Drawing.Color]::White
$titreVue.Font = New-Object System.Drawing.Font('Segoe UI', 13)
$titreVue.SetBounds(20, 12, 1380, 32)
$journalVue = New-Object System.Windows.Forms.Label
$journalVue.AutoSize = $false
$journalVue.BackColor = $form.BackColor
$journalVue.ForeColor = [System.Drawing.Color]::FromArgb(225, 232, 240)
$journalVue.Font = New-Object System.Drawing.Font('Consolas', 10)
$journalVue.SetBounds(20, 55, 1380, (22 * $lignes.Count))
$journalVue.Text = $lignes -join [Environment]::NewLine
$form.Controls.Add($titreVue)
$form.Controls.Add($journalVue)
$form.CreateControl()
$journalVue.CreateControl()
$form.Show()
[System.Windows.Forms.Application]::DoEvents()
$bitmap = New-Object System.Drawing.Bitmap($form.Width, $form.Height)
try {
    $rectangle = New-Object System.Drawing.Rectangle(0, 0, $form.Width, $form.Height)
    $form.DrawToBitmap($bitmap, $rectangle)
    $bitmap.Save([System.IO.Path]::GetFullPath($Image), [System.Drawing.Imaging.ImageFormat]::Png)
} finally {
    $bitmap.Dispose()
    $form.Dispose()
}
