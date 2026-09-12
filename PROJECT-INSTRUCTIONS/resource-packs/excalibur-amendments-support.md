# Excalibur | Amendments Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81b78540f4c7e92167d7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Amendments 1.7.zip`
- **Versão 1.21.1:** 1.7
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Amendments 1.7.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Amendments `1.21-2.1.10`. O requisito `Pixel Consistent = OFF` é preservado como gate operacional porque load order sozinho não resolve a superfície de signs quando essa opção está ligada.

## Propriedades do banco

- **Mod:** Excalibur | Amendments Support
- **Arquivo JAR:** `Excalibur Amendments 1.7.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Support pack 16x para Amendments no estilo Excalibur, com retextures/models para wall lanterns, double cakes/eating progress, hanging signs, candles on skulls e outros assets decorativos.
- **Dependências:** Uso visual pretendido: Excalibur + Amendments 1.21-2.1.10. Requisito operacional oficial: Amendments → Client → Sign → `Pixel Consistent` deve ficar DESATIVADO para os overrides de signs funcionarem corretamente.
- **Sobreposição:** Sobrepõe assets de Amendments e pode interagir visualmente com Farmer's Delight/sign packs. `Pixel Consistent` ligado pode impedir overrides mesmo com load order correto.
- **Compatibilidade/Riscos:** GATE DE CONFIG: `Pixel Consistent` ligado força signs como block models usando texturas vanilla não substituíveis e pode tornar signs de mods como Farmer's Delight invisíveis. Riscos adicionais: load order, model drift e conflitos com outros retextures.
- **Observações:** Arquivo instalado `Excalibur Amendments 1.7.zip`, release 1.7 de 07/12/2025 para 1.21.1. O projeto antigo de nome semelhante não é a ficha canônica deste arquivo.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Amendments 1.7 e instrução Pixel Consistent.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-amendments
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Amendments 2.1.10, v1.7, requisito Pixel Consistent OFF, signs/cakes/lanterns/skulls, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Amendments 1.7.zip`, versão `1.7`, para Minecraft 1.21.1. O alvo físico é Amendments `1.21-2.1.10`.

## 1. Papel e authority
O support pack altera assets de Amendments; o mod continua authority de blocks, interactions, cake progress, signs e demais comportamentos.

## 2. Cobertura confirmada
O upstream cita wall lanterns, double cakes e eating progress, custom hanging-sign models, candles on skulls e outros elementos decorativos. Não extrapolar essa lista para todo o mod sem inventário do ZIP.

## 3. Requisito crítico — Pixel Consistent
A documentação oficial exige desativar **Amendments → Client → Sign → Pixel Consistent**. Quando ligado, signs são renderizadas como block models, forçando texturas vanilla que não podem ser sobrescritas pelo support pack. O upstream também alerta que isso pode deixar signs de mods como Farmer's Delight invisíveis.
Esse ajuste é parte do contract operacional do pack visual, não mera preferência estética.

## 4. Stack físico e load order
Amendments físico: `1.21-2.1.10`. O resource pack deve ficar acima do Excalibur base; porém load order sozinho não corrige signs enquanto `Pixel Consistent` estiver ativo.

## 5. Client e reload
Config e resource pack atuam no cliente. Resource reload deve atualizar textures/models sem alterar block state ou gameplay. Alterar `Pixel Consistent` pode exigir refresh visual para validar o resultado.

## 6. Riscos
1. Pixel Consistent ligado neutralizar signs do pack.
2. Signs de outros mods ficarem invisíveis.
3. Models de cake/sign mudarem em Amendments 2.1.10.
4. Outro support pack sobrescrever lantern/skull/cake assets.
5. Resource reload manter model cache stale.

## 7. Matriz de testes
- [ ] Confirmar `Pixel Consistent = OFF`.
- [ ] Conferir hanging signs do Amendments.
- [ ] Conferir signs de Farmer's Delight/outros mods relevantes.
- [ ] Wall lanterns e candles on skulls corretos.
- [ ] Double cakes e eating-progress models corretos.
- [ ] Resource reload sem missing models/textures.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.7 e publica explicitamente a exigência de desligar Pixel Consistent, além dos exemplos de cobertura. O ZIP não foi inventariado integralmente.

> Boundary canônico: sem `Pixel Consistent = OFF`, a compatibilidade visual de signs não deve ser considerada validada.
