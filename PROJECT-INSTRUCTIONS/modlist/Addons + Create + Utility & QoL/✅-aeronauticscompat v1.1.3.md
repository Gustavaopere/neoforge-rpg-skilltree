# AeronauticsCompat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist(1).txt` de 16/09/2026 — autoridade física atual
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AeronauticsCompat
- **Arquivo JAR:** `aeronauticscompat-1.1.3.jar`
- **Versão 1.21.1:** `1.1.3`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 14/09/2026 — modelo de patches condicionais, ownership, classloading, lifecycle, dedicated-server QA e fingerprint físico documentados; regra fail-closed para targets Alex's Continued preservada.
- **Categoria:** Compat; QoL
- **Compatibilidade/Riscos:** Patches podem ficar obsoletos após updates dos mods alvo. Alex's Caves/Mobs corrigidos upstream são ports de Raguto; o pack usa Continued de CodxIO, portanto não assumir cobertura sem validar targets. Storage Drawers mantém bug visual conhecido mesmo com fix upstream.
- **Decisão:** Sem decisão
- **Dependências:** Sable é o requisito técnico central; Create Aeronautics não é tecnicamente obrigatório segundo o autor. Mods alvo são condicionais. No pack atual, Create Bits 'n' Bobs 2.3.2 é alvo confirmado presente.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-compatability
- **Função:** Pacote de mixins/patches que corrige mods incompatíveis com physics-enabled ships do Sable; cobre coordenadas, interação, áudio, pathfinding, montagem e outros casos específicos por mod alvo.
- **Histórico da decisão:** vazio
- **Observações:** Cobertura upstream é potencial, não prova de aplicação no pack. Para Alex's Caves/Mobs Continued, validar classes/targets e log de mixin antes de atribuir cobertura. Bits 'n' Bobs 2.3.2 permanece alvo físico confirmado.
- **Procedência:** modlist.txt física do projeto consultada em 14/09/2026 + CurseForge oficial Create Aeronautics: Compatibility 1.1.3. Artefato `aeronauticscompat-1.1.3.jar`, runtime `1.1.3`, SHA-1 `4d1aea2d6e27705e284d4603aa3422d84464e406`, mixin `aeronauticscompat.mixins.json`.
- **Sobreposição:** O suporte de câmera 1.1.3 mira Camera Mod de henkelmax; não é evidência de redundância com Aeronautics Camera Sync 1.4.0. Bits 'n' Bobs é cobertura efetivamente relevante no pack atual.
- **Data da última decisão:** vazio
- **Estado no pack:** Integrado ao Github

## Escopo e papel
Pacote geral de patches para mods que não se comportam corretamente em **physics-enabled ships do Sable**. O próprio autor esclarece que o requisito técnico central é Sable, não Create Aeronautics em si: o projeto corrige integrações de mods que não usam Sable Companion ou assumem coordenadas/mundo estático.

## Runtime e autoridade
- JAR físico: `aeronauticscompat-1.1.3.jar`.
- Mod ID: `aeronauticscompat`.
- Runtime: `1.1.3`.
- Release oficial NeoForge 1.21.1 de 10/07/2026.

## Cobertura upstream documentada
A página oficial lista patches para Etched, WATERFrAMES, Alex's Mobs (port não oficial de Raguto), Alex's Caves (port não oficial de Raguto), Another Furniture, Immersive Paintings, Thick Air, Create: Bits 'n' Bobs, PneumaticCraft: Repressurized, Sleep Tight, Cobblemon, Storage Drawers e Camera Mod de henkelmax. A 1.1.3 acrescentou suporte de câmera e Storage Drawers e atualizou a versão de Sable.

## Relações com o pack atual
- `Create Bits 'n' Bobs 2.3.2` está presente e é um alvo upstream explícito; o patch documentado evita duplicação de chains ao montar/desmontar ships.
- O pack possui `Aeronautics Camera Sync 1.4.0`, mas **não foi encontrado Camera Mod de henkelmax**. Portanto o “camera support” do AeronauticsCompat não deve ser tratado como duplicata do Camera Sync: são alvos diferentes.
- O pack usa **Alex's Caves Continued** e **Alex's Mobs Continued**, não os ports de Raguto citados pela página. Não há base suficiente para afirmar que os mixins específicos de Alex se aplicam às builds Continued; isso precisa ser validado por classe/target/runtime.
- Não foram encontrados top-level de Storage Drawers, PneumaticCraft ou Immersive Paintings no snapshot atual.

## Compatibilidade, sobreposição e riscos
Por ser um conjunto de mixins condicionais, o risco maior é patch obsoleto após atualização do mod alvo ou mudança de classes. Fixes não devem ser considerados necessários apenas porque o projeto os lista; a presença do alvo e a versão precisam ser confirmadas. O patch de Storage Drawers declara corrigir crash, mas deixa bug visual conhecido.

## Limites
Não é um framework de física, não substitui Sable Companion e não adiciona conteúdo de gameplay autônomo. Também não transforma automaticamente qualquer mod em compatível com ships.


## Ownership técnico e modelo de patch
- **Ownership primário:** corrigir incompatibilidades de mods terceiros quando suas entidades/blocos/interações entram em physics ships do Sable.
- **Mod ID:** `aeronauticscompat`.
- **Mixin config físico:** `aeronauticscompat.mixins.json`.
- O projeto é um agregador de patches **condicionais por alvo**. Portanto, a lista upstream descreve cobertura potencial; uma integração só deve ser considerada ativa quando o mod alvo existe e os mixin targets correspondem à implementação instalada.
- Isso é especialmente importante para Alex's Mobs/Caves: o upstream cita ports diferentes, enquanto o pack usa as variantes Continued de CodxIO.
## Configuração e dados
Nenhuma configuração de gameplay própria foi confirmada neste lote. A superfície operacional principal é o conjunto de mixins e a presença/ausência de classes dos mods alvo. Logs de aplicação de mixin são evidência mais forte do que o simples fato de o projeto listar determinado mod na descrição.
## Client/server, classloading e lifecycle
Patches podem atingir código comum, client-only ou server-side dependendo do alvo. Como o arquivo físico agrega múltiplas compatibilidades, um risco central é **classloading indevido de alvo opcional ausente** ou aplicação contra assinatura incompatível após update. O lifecycle de validação deve cobrir bootstrap, entrada em mundo, montagem/desmontagem de ship, save/reload e dedicated server.
## Fingerprint físico
- JAR: `aeronauticscompat-1.1.3.jar`
- Runtime: `1.1.3`
- SHA-1: `4d1aea2d6e27705e284d4603aa3422d84464e406`
- Mixin config: `aeronauticscompat.mixins.json`


## Reconciliação física atual — 18/09/2026
A modlist física mais recente (`modlist(1).txt`, 16/09/2026) mantém `aeronauticscompat-1.1.3.jar`, mas o alvo Create Bits 'n' Bobs avançou de `2.3.2` para `2.3.5`. O patch para Bits 'n' Bobs continua devendo ser validado por aplicação real de mixin/runtime; a presença da versão mais nova não autoriza presumir cobertura sem teste.

## Testes recomendados
1. Bits 'n' Bobs: montar/desmontar ships com chains e confirmar ausência de duplicação.
2. Verificar log de mixins para targets opcionais ausentes sem erro fatal.
3. Testar qualquer mod alvo que seja adicionado futuramente dentro de ship móvel, incluindo save/reload.
4. Para Alex's Continued, validar explicitamente se os mixins carregam/aplicam antes de atribuir cobertura.
5. Dedicated-server smoke para detectar classloading indevido de patches client-only.

## Evidências
- [CurseForge oficial — Create Aeronautics: Compatibility](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics-compatability)
- Modlist física atual e guia consolidado de Tecnologia.
